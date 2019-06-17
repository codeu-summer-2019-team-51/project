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

/** Fetches about book data and adds it to the page. */
function fetchAboutBook() {
  const url = `/title?book=${parameterBookname}`;
  fetch(url)
    .then(response => response.text())
    .then((response) => {
      const bookContainer = document.getElementById('book-info');
      let aboutBook = response;
      if (response === '') {
        aboutBook = 'No information yet.';
      }
      bookContainer.innerHTML = aboutBook;
    });
}

/** Fetches data and populates the UI of the page. */
function buildUI() {
  setPageTitle();
  fetchAboutBook();
}
