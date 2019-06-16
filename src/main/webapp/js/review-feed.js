function buildReviewDiv(review) {
  const usernameDiv = document.createElement('div');
  usernameDiv.classList.add('left-align');
  usernameDiv.appendChild(document.createTextNode(review.author));

  const timeDiv = document.createElement('div');
  timeDiv.classList.add('left-align');
  timeDiv.appendChild(document.createTextNode(new Date(review.timestamp)));

  const headerDiv = document.createElement('div');
  headerDiv.classList.add('message-header');
  headerDiv.appendChild(usernameDiv);
  headerDiv.appendChild(timeDiv);

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('message-body');
  bodyDiv.appendChild(document.createTextNode(review.comment));

  const reviewDiv = document.createElement('div');
  reviewDiv.classList.add('message-div');
  reviewDiv.appendChild(headerDiv);
  reviewDiv.appendChild(bodyDiv);

  return reviewDiv;
}

// Fetch messages and add them to the page.
function fetchReviews() {
  const url = '/reviewFeed';
  fetch(url).then(response => response.json())
    .then((reviews) => {
      const reviewContainer = document.getElementById('review-container');
      if (reviews.length === 0) {
        reviewContainer.innerHTML = '<p>There are no posts yet.</p>';
      } else {
        reviewContainer.innerHTML = '';
      }
      reviews.forEach((review) => {
        const reviewDiv = buildReviewDiv(review);
        reviewContainer.appendChild(reviewDiv);
      });
    });
}

// Fetch data and populate the UI of the page.
function buildUI() { // eslint-disable-line no-unused-vars
  fetchReviews();
}
