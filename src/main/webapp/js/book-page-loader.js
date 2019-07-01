

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
    .then((bookData) => {
        document.getElementById('page-title').innerText = bookData.title;
        document.title = bookData.title + ' - Book Page'; // eslint-disable-line prefer-template
    });
}

/** Fetches book title and adds it to the page. */
function fetchBookTitle() {
  const url = `/about-book?book=${parameterBookId}`;
  fetch(url)
    .then(response => response.json())
    .then((bookData) => {
      const bookContainer = document.getElementById('book-title');
      if (bookData === '') {
        bookContainer.innerHTML='No information yet.';
      }
      else{
        bookContainer.innerHTML=bookData.title;
      }
    });
}

/** Fetches book author(s) and adds them to the page. */
function fetchBookAuthors() {
  const url = `/about-book?book=${parameterBookId}`;
  fetch(url)
    .then(response => response.json())
    .then((bookData) => {
      const bookContainer = document.getElementById('book-authors');
      if (bookData === '') {
         bookContainer.innerHTML = 'No information yet.';
      }
      const authorDiv = buildAuthorDiv(bookData);
      bookContainer.innerHTML = bookData.authors;
    });
}

function buildAuthorDiv(aboutBook) {
const authorDiv = document.createElement('div');
  authorDiv.classList.add('message-div');
  authorDiv.innerHTML = aboutBook.authors;

  return authorDiv;
}

/** Fetches data and populates the UI of the page. */
function buildUI() { // eslint-disable-line no-unused-vars
  setPageTitle();
  fetchBookTitle();
  fetchBookAuthors();
}
