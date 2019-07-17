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

// Get ?book=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
console.log(urlParams);
const parameterBookId = urlParams.get('id');
console.log(parameterBookId);

// URL must include ?book=XYZ parameter. If not, redirect to homepage.
if (!parameterBookId) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter bookId. */
function setPageTitle() {
 const url = `/about-book?book=${parameterBookId}`;
  fetch(url)
    .then(response => response.json())
    .then((book) => {
        document.title = `${book.title} - Book Page`;
    });
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
  headerDiv.innerHTML = `<b>${review.author}</b> ${date}`;


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
 * Shows the message form if the user is logged in and viewing their own page.
 */
function showReviewFormIfLoggedIn() {
  fetch('/login-status')
    .then(response => response.json())
    .then((loginStatus) => {
      if (loginStatus.isLoggedIn) {
        show('review-form');
      }
    });
}

/** Fetches messages and adds them to the page. */
function fetchReviews() {
  const url = `/reviews?bookId=${parameterBookId}`;
  fetch(url)
    .then(response => response.json())
    .then((reviews) => {
      const reviewsContainer = document.getElementById('review-container');
      if (reviews.length === 0) {
        reviewsContainer.innerHTML = '<p>This book has no reviews yet.</p>';
      } else {
        reviewsContainer.innerHTML = '';
      }
      reviews.forEach((review) => {
        const reviewDiv = buildReviewDiv(review);
        reviewsContainer.appendChild(reviewDiv);
      });
    });
}

/** Fetches book title and authors and adds it to the page. */
function fetchBook() {
  const url = `/about-book?book=${parameterBookId}`;
  fetch(url)
    .then(response => response.json())
    // throws SyntaxError: Unexpected end of JSON input
    // when parameterBookId does not belong to any book
    // TODO: handle such cases gracefully
    .then((book) => {
      const titleDiv = document.getElementById('book-title');
      titleDiv.innerText = book.title;

      const authorsDiv = document.getElementById('book-authors');
      authorsDiv.innerText = `by ${book.authors}`;

      const ratingDiv = document.getElementById('book-rating');
      ratingDiv.innerText = '';
      ratingDiv.classList.add('book-rating');
      const rating = book.avgRating;
      const title = book.title;
      for (i = 0; i < 5; i++) {
        const starDiv = document.createElement('div');
        starDiv.classList.add('star');

        let starFill = rating - i;
        if (starFill < 0.5) {
          starFill = 0;
        } else if (starFill < 1) {
          starFill = 5;
        } else {
          starFill = 10;
        }
        starDiv.classList.add(`star-${starFill}`);
        ratingDiv.appendChild(starDiv);
      }
    });
}


function setReviewFormBookInput() {
  const reviewBook = document.getElementById('review-book');
  reviewBook.value = parameterBookId;
}

/** Fetches data and populates the UI of the page. */
function buildUI() { // eslint-disable-line no-unused-vars
  showReviewFormIfLoggedIn();
  setPageTitle();
  setReviewFormBookInput();
  fetchBook();
  fetchReviews();
}
