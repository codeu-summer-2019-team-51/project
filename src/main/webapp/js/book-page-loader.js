

// Get ?book=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const parameterBookname = urlParams.get('book');
console.log(19);
console.log(parameterBookname);
// const parameterBookname = "4a785b44-d3d7-47f7-a936-6c49137cbd53";

// URL must include ?book=XYZ parameter. If not, redirect to homepage.
if (!parameterBookname) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter bookname. */
function setPageTitle() {
  document.getElementById('page-title').innerText = parameterBookname;
  document.title = parameterBookname + ' - Book Page'; // eslint-disable-line prefer-template
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

// function showData() {
//
//        show('about-book-form');
//
// }

/** Fetches data and populates the UI of the page. */
function buildUI() { //eslint-disable-line no-unused-vars
  setPageTitle();
  fetchBookTitle();
  fetchBookAuthors();
}
