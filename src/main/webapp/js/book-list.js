function buildBookDiv(book) {
  const titleDiv = document.createElement('div');
  titleDiv.classList.add('left-align');
  titleDiv.classList.add('book-title');
  titleDiv.appendChild(document.createTextNode(book.title));

  const authorsDiv = document.createElement('div');
  authorsDiv.classList.add('left-align');
  authorsDiv.classList.add('book-authors');
  authorsDiv.appendChild(document.createTextNode(book.authors.join()));

  const ratingDiv = document.createElement('div');
  ratingDiv.classList.add('left-align');
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
  bookDiv.appendChild(titleDiv);
  bookDiv.appendChild(authorsDiv);
  bookDiv.appendChild(ratingDiv);

  return bookDiv;
}

function buildReadingStatusButton(userBook, readingStatus) {
  const button = document.createElement('button');
  button.classList.add('reading-status');
  button.innerText = readingStatusText[readingStatus];

  if (userBook.status === readingStatus) {
    button.classList.add('reading-status-set');
  } else {
    button.classList.add('button-clear');
    button.onclick = () => {
      $.ajax({
        type: 'POST',
        url: '/user-book',
        data: {
          bookId: userBook.bookId,
          status: readingStatus
        },
        success: () => {
          window.location = 'book-list.html';
        }
      });
    };
  }

  return button;
}

function buildAddToShelfDiv(userBook) {
  const addToShelfDiv = document.createElement('div');
  addToShelfDiv.id = `add-to-shelf-${userBook.bookId}`;

  const button = document.createElement('button');
  button.classList.add('add-to-shelf-button');
  button.onclick = () => {
    const dropdown = document.getElementById(`dropdown-${userBook.bookId}`)
    dropdown.classList.toggle('hidden');
  };
  if (userBook.user) {
    button.classList.add('added-to-shelf-button');
    button.innerText = 'Move to shelf';
  } else {
    button.innerText = 'Add to shelf';
  }

  const dropdown = document.createElement('div');
  dropdown.id = `dropdown-${userBook.bookId}`;
  dropdown.classList.add('dropdown');
  dropdown.classList.add('hidden');

  Object.keys(readingStatusText).forEach((readingStatus) => {
    dropdown.appendChild(buildReadingStatusButton(userBook, readingStatus));
  });

  addToShelfDiv.appendChild(button);
  addToShelfDiv.appendChild(dropdown);

  return addToShelfDiv;
}

// Fetch books and add them to the page.
function fetchBooks() {
  const allBooks = fetch('/book-list').then(response => response.json());
  const loginStatus = fetch('/login-status').then(response => response.json());
  const userBooks = loginStatus.then((loginStatus) => {
    if (loginStatus.isLoggedIn) {
      return fetch(`/user-book?user=${loginStatus.username}`)
        .then(response => response.json())
        .then((userBookList) => {
          const userBookMap = {};
          userBookList.forEach((userBook) => {
            userBookMap[userBook.bookId] = userBook;
          });
          return userBookMap;
        });
    } else {
      return {};
    }
  });

  Promise.all([allBooks, userBooks])
    .then(response => {
      const [allBooks, userBooks] = response;
      const bookContainer = document.getElementById('book-container');
      if (allBooks.length === 0) {
        bookContainer.innerHTML = '<p>There are no books yet.</p>';
        return;
      }
      bookContainer.innerHTML = '';

      allBooks.forEach((book) => {
        const bookDiv = buildBookDiv(book);
        const userBook = userBooks[book.id] || {bookId: book.id};
        const addToShelfDiv = buildAddToShelfDiv(userBook);
        bookDiv.appendChild(addToShelfDiv);
        bookContainer.appendChild(bookDiv);
        return bookDiv;
      });

      window.onclick = (event) => {
        if (!event.target.matches('.add-to-shelf-button')) {
          const dropdowns = document.getElementsByClassName("dropdown");
          for (const dropdown of dropdowns) {
            dropdown.classList.add('hidden');
          }
        }
      }
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
