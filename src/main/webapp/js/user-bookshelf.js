const urlParams = new URLSearchParams(window.location.search);
const user = urlParams.get('user');

// Fetch data and populate the UI of the page.
function buildUI() { // eslint-disable-line no-unused-vars
  fetchContent();
}

// Fetch community and threads and add them to the page.
function fetchContent() {
  const url = `/user-book?user=${user}`;
  fetch(url)
    .then(response => response.json())
    .then((result) => {
      console.log(result);
      loadUserBooks(result);
    });
}

function loadUserBooks(userBooks) {
  const userBookContainer = document.getElementById('user-book-container');
  if (userBooks.length === 0) {
    userBookContainer.innerHTML = '<p>There are no books on the shelf.</p>';
  } else {
    userBookContainer.innerHTML = '';
  }
  userBooks.forEach((userBook) => {
    const userBookDiv = buildUserBookDiv(userBook);
    userBookContainer.appendChild(userBookDiv);
  });
}

function buildUserBookDiv(userBook) {
  const titleDiv = document.createElement('div');
  titleDiv.classList.add('book-title');
  titleDiv.appendChild(document.createTextNode(userBook.book.title));

  const authorsDiv = document.createElement('div');
  authorsDiv.classList.add('book-authors');
  authorsDiv.appendChild(document.createTextNode(userBook.book.authors.join()));

  const readingStatusDiv = document.createElement('div');
  readingStatusDiv.classList.add('reading-status');
  readingStatusDiv.appendChild(document.createTextNode(userBook.status));

  const timestampDiv = document.createElement('div');
  timestampDiv.classList.add('timestamp');
  timestampDiv.appendChild(document.createTextNode(userBook.timestamp));

  const bookDiv = document.createElement('div');
  bookDiv.classList.add('book-div');
  bookDiv.appendChild(titleDiv);
  bookDiv.appendChild(authorsDiv);
  bookDiv.appendChild(readingStatusDiv);
  bookDiv.appendChild(timestampDiv);

  return bookDiv;
}
