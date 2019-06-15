function buildBookDiv(book) {
  const titleDiv = document.createElement('div');
  titleDiv.classList.add('left-align');
  titleDiv.appendChild(document.createTextNode(book.title));

  const authorsDiv = document.createElement('div');
  authorsDiv.classList.add('left-align');
  authorsDiv.appendChild(document.createTextNode(book.authors.join()));

  const headerDiv = document.createElement('div');
  headerDiv.classList.add('book-title');
  headerDiv.appendChild(titleDiv);

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('book-authors');
  bodyDiv.appendChild(authorsDiv);

  const bookDiv = document.createElement('div');
  bookDiv.classList.add('book-div');
  bookDiv.appendChild(headerDiv);
  bookDiv.appendChild(bodyDiv);

  return bookDiv;
}

// Fetch books and add them to the page.
function fetchBooks() {
  const url = '/book-list';
  fetch(url).then(response => response.json())
    .then((books) => {
      const bookContainer = document.getElementById('book-container');
      if (books.length === 0) {
        bookContainer.innerHTML = '<p>There are no books yet.</p>';
      } else {
        bookContainer.innerHTML = '';
      }
      books.forEach((book) => {
        const bookDiv = buildBookDiv(book);
        bookContainer.appendChild(bookDiv);
      });
    });
}

// Fetch data and populate the UI of the page.
function buildUI() { // eslint-disable-line no-unused-vars
  fetchBooks();
}
