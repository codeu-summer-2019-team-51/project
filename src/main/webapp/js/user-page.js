/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Get ?user=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
console.log(urlParams);
const user = urlParams.get('user');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!user) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter username. */
function setPageTitle() {
  document.getElementById('page-title').innerText = user;
  document.title = user + ' - User Page';
}

/**
 * Builds an element that displays the review.
 * @param {Review} review
 * @return {Element}
 */
 function buildReviewDiv(review) {
   let date = new Date(review.timestamp)
   const options = {
     year: 'numeric',
     month: 'short',
     day: 'numeric',
     hour: 'numeric',
     minute: '2-digit'
   };
   date = date.toLocaleDateString('default', options);

   const headerDiv = document.createElement('div');
   headerDiv.classList.add('review-header');
   headerDiv.innerHTML = `<b>${review.bookName}</b> ${date}`;


   const bodyDiv = document.createElement('div');
   bodyDiv.classList.add('review-body');
   bodyDiv.innerHTML = `<i>(${review.rating}/5)</i> ${review.comment}`;

   const reviewDiv = document.createElement('div');
   reviewDiv.classList.add('review-div');
   reviewDiv.appendChild(headerDiv);
   reviewDiv.appendChild(bodyDiv);

   return reviewDiv;
 }

/**
 * Shows html element with specified elementId.
 */
function show(elementId) {
  const element = document.getElementById(elementId);
  element.classList.remove('hidden');
}

/**
 * Hides html element with specified elementId.
 */
function hide(elementId) { // eslint-disable-line no-unused-vars
  const element = document.getElementById(elementId);
  element.classList.add('hidden');
}

/**
 * Shows the review form if the user is logged in and viewing their own page.
 */
function showEditButtonIfViewingSelf() {
  fetch('/login-status')
    .then(response => response.json())
    .then((loginStatus) => {
      if (loginStatus.isLoggedIn &&
          loginStatus.username === user) {
        show('profile-pic-editor');
        show('about-me-editor');
      }
    });
}

/** Fetches reviews and adds them to the page. */
function fetchReviews() {
  const url = `/reviews?user=${user}`;
  fetch(url)
    .then(response => response.json())
    .then((reviews) => {
      const reviewsContainer = document.getElementById('review-container');
      if (reviews.length === 0) {
        reviewsContainer.innerHTML = '<p>This user has no posts yet.</p>';
      } else {
        reviewsContainer.innerHTML = '';
      }
      reviews.forEach((review) => {
        const reviewDiv = buildReviewDiv(review);
        reviewsContainer.appendChild(reviewDiv);
      });
    });
}

/** Fetches about me data and adds them to the page. */
function fetchAboutMe() {
  const url = `/about?user=${user}`;
  fetch(url)
    .then(response => response.text())
    .then((response) => {
      const aboutMeContainer = document.getElementById('about-me-container');
      let aboutMe = response;
      if (response === '') {
        aboutMe = 'This user has not entered any information yet.';
      }
      aboutMeContainer.innerHTML = aboutMe;
    });
}

/** Fetches profile picture and adds it to the page. */
function fetchProfilePic() {
  const url = `/profile-pic?user=${user}`;
  fetch(url)
    .then(response => response.text())
    .then((response) => {
      const profilePic = document.getElementById('profile-pic');
      let filePath = response;
      if (response === '') {
        filePath = 'image/profile-pic.png';
      }
      profilePic.src = filePath;
    });
}

/** Fetches data and populates the UI of the page. */
function buildUI() {
  setPageTitle();
  showEditButtonIfViewingSelf();
  fetchProfilePic();
  fetchAboutMe();
  fetchReviews();
}
