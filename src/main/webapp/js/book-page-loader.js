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
const parameterUsername = urlParams.get('book');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterUsername) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter username. */
function setPageTitle() {
  document.getElementById('page-title').innerText = parameterUsername;
  document.title = parameterUsername + ' - Book Page';
}

/**
 * Builds an element that displays the message.
 * @param {Message} message
 * @return {Element}
 */
function buildReviewDiv(review) {
  const headerDiv = document.createElement('div');
  const reviewDate = new Date(review.timestamp);
  headerDiv.classList.add('review-header');
  headerDiv.appendChild(
    document.createTextNode(`${review.author} - ${messageDate}`)
  );

  const headerColumn = document.createElement('div');
  headerColumn.classList.add('column');
  headerColumn.appendChild(headerDiv);

  const headerRow = document.createElement('div');
  headerRow.classList.add('row');
  headerRow.appendChild(headerColumn);

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('review-body');
  bodyDiv.innerHTML = review.comment;

  const bodyColumn = document.createElement('div');
  bodyColumn.classList.add('column');
  bodyColumn.appendChild(bodyDiv);

  const bodyRow = document.createElement('div');
  bodyRow.classList.add('row');
  bodyRow.appendChild(bodyColumn);

  const reviewDiv = document.createElement('div');
  reviewDiv.classList.add('container');
  reviewDiv.classList.add('review-div');
  reviewDiv.appendChild(headerRow);
  reviewDiv.appendChild(bodyRow);

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
 * Shows the message form if the user is logged in and viewing their own page.
 */
function showReviewFormIfViewingSelf() {
  fetch('/login-status')
    .then(response => response.json())
    .then((loginStatus) => {
      if (loginStatus.isLoggedIn &&
        loginStatus.username === parameterUsername) {
        show('review-form');
        show('profile-pic-editor');
        show('about-me-editor');
      }
    });
}

/** Fetches messages and adds them to the page. */
function fetchReviews() {
  const url = `/reviews?user=${parameterUsername}`;
  fetch(url)
    .then(response => response.json())
    .then((reviews) => {
      const reviewsContainer = document.getElementById('review-container');
      if (reviews.length === 0) {
        reviewsContainer.innerHTML = '<p>This book has no reviews yet.</p>';
      } else {
        reviewsContainer.innerHTML = '';
      }
      reviews.forEach((message) => {
        const reviewsDiv = buildReviewDiv(message);
        reviewsContainer.appendChild(reviewDiv);
      });
    });
}

/** Fetches about me data and adds them to the page. */
function fetchAboutMe() {
  const url = `/about?user=${parameterUsername}`;
  fetch(url)
    .then(response => response.text())
    .then((response) => {
      const aboutMeContainer = document.getElementById('about-me-container');
      let aboutMe = response;
      if (response === '') {
        aboutMe = 'This book has no information yet.';
      }
      aboutMeContainer.innerHTML = aboutMe;
    });
}

/** Fetches profile picture and adds it to the page. */
function fetchProfilePic() {
  const url = `/profile-pic?user=${parameterUsername}`;
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
  showReviewFormIfViewingSelf();
  fetchProfilePic();
  fetchAboutMe();
  fetchReviews();
}
