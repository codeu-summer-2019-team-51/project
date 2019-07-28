function buildRatingDivContent(ratingDiv, rating) {
  ratingDiv.innerText = '';
  ratingDiv.classList.add('star-rating');
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
}

function buildReviewDiv(review) {
  const reviewDiv = document.createElement('div');
  reviewDiv.classList.add('review-div');

  let date = new Date(review.timestamp)
  const options = {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: 'numeric',
    minute: '2-digit'
  };
  date = date.toLocaleDateString('default', options);

  const dateDiv = document.createElement('div');
  dateDiv.classList.add('review-date');
  dateDiv.innerHTML = date;
  reviewDiv.appendChild(dateDiv);

  const userLink = `<a href="/user-page.html?user=${review.author}" style="color: #626e78;">${review.author}</a>`;
  const bookLink = `<a href="/aboutbook.html?id=${review.bookId}" style="color: #626e78;">${review.bookName}</a>`;

  const headerDiv = document.createElement('div');
  headerDiv.classList.add('review-header');
  headerDiv.innerHTML = `<b>${userLink}</b> reviewed <h4>${bookLink}</h4>`;
  reviewDiv.appendChild(headerDiv);

  const ratingDiv = document.createElement('div');
  buildRatingDivContent(ratingDiv, review.rating);
  reviewDiv.appendChild(ratingDiv);

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('review-body');
  bodyDiv.appendChild(document.createTextNode(review.comment));
  reviewDiv.appendChild(bodyDiv);

  return reviewDiv;
}

// Fetch reviews and add them to the page.
function fetchReviews() {
  const url = '/review-feed';
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
