function buildBookDiv(book) {
  const bookLink = document.createElement('a');
  bookLink.href = `/aboutbook.html?id=${book.id}`;

  const titleDiv = document.createElement('h3');
  titleDiv.classList.add('book-title');
  titleDiv.appendChild(document.createTextNode(book.title));

  bookLink.appendChild(titleDiv);

  const authorsDiv = document.createElement('div');
  authorsDiv.classList.add('book-authors');
  authorsDiv.appendChild(document.createTextNode(book.authors.join()));

  const ratingDiv = document.createElement('div');
  ratingDiv.classList.add('book-rating');
  const rating = book.avgRating;
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

  const bookDiv = document.createElement('div');
  bookDiv.classList.add('book-div');
  bookDiv.appendChild(bookLink);
  bookDiv.appendChild(authorsDiv);
  bookDiv.appendChild(ratingDiv);

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

function searchBooks() { // eslint-disable-line no-unused-vars
  const input = document.getElementById('book-search');
  const filter = input.value.toUpperCase();
  const bookContainer = document.getElementById('book-container');
  const books = bookContainer.getElementsByClassName('book-div');

  Array.from(books).forEach((bookDiv) => {
    const title = bookDiv.getElementsByClassName('book-title')[0].innerText;
    const authors = bookDiv.getElementsByClassName('book-authors')[0].innerText;
    console.log(title);
    console.log(title.toUpperCase().indexOf(filter));
    console.log(authors.toUpperCase().indexOf(filter));
    if (title.toUpperCase().indexOf(filter) < 0
        && authors.toUpperCase().indexOf(filter) < 0) {
      bookDiv.classList.add('hidden');
    } else {
      bookDiv.classList.remove('hidden');
    }
  });
}
