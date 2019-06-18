// Get ?book=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const parameterBookname = urlParams.get('book');

// URL must include ?book=XYZ parameter. If not, redirect to homepage.
if (!parameterBookname) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter bookname. */
function setPageTitle() {
  document.getElementById('page-title').innerText = parameterBookname;
  document.title = parameterBookname + ' - Book Page';
}

/** Fetches book title and adds it to the page. */
function fetchBookTitle() {
  const url = `/title?book=${parameterBookname}`;
  fetch(url)
    .then(response => response.text())
    .then((response) => {
      const bookContainer = document.getElementById('book-title');
      let aboutBook = response;
      if (response === '') {
        aboutBook = 'No information yet.';
      }
      bookContainer.innerHTML = aboutBook;
    });
}

/** Fetches book author(s) and adds them to the page. */
function fetchBookAuthors() {
  const url = `/authors?book=${parameterBookname}`;
  fetch(url)
    .then(response => response.text())
    .then((response) => {
      const bookContainer = document.getElementById('book-authors');
      let aboutBook = response;
      if (response === '') {
        aboutBook = 'No information yet.';
      }
      bookContainer.innerHTML = aboutBook;
    });
}

function showData() {

        show('about-book-form');

}

/** Fetches data and populates the UI of the page. */
function buildUI() {
  setPageTitle();
  fetchBookTitle();
  fetchBookAuthors();

}
