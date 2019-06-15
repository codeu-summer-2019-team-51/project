function buildBookDiv(book) {
  const titleDiv = document.createElement('div');
  titleDiv.classList.add('left-align');
  titleDiv.classList.add('book-title');
  titleDiv.appendChild(document.createTextNode(book.title));

  const authorsDiv = document.createElement('div');
  authorsDiv.classList.add('left-align');
  authorsDiv.classList.add('book-authors');
  authorsDiv.appendChild(document.createTextNode(book.authors.join()));

  const bookDiv = document.createElement('div');
  bookDiv.classList.add('book-div');
  bookDiv.appendChild(titleDiv);
  bookDiv.appendChild(authorsDiv);

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

function searchBooks() {
  let input = document.getElementById('book-search');
  let filter = input.value.toUpperCase();
  let bookContainer = document.getElementById('book-container');
  let books = bookContainer.getElementsByClassName('book-div');

  for (let bookDiv of books) {
    let title = bookDiv.getElementsByClassName('book-title')[0].innerText;
    let authors = bookDiv.getElementsByClassName('book-authors')[0].innerText;
    console.log(title);
    console.log(title.toUpperCase().indexOf(filter));
    console.log(authors.toUpperCase().indexOf(filter));
    if (title.toUpperCase().indexOf(filter) < 0
        && authors.toUpperCase().indexOf(filter) < 0) {
      bookDiv.classList.add('hidden');
    } else {
      bookDiv.classList.remove('hidden');
    }
  }
}
