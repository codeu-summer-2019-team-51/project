const urlParams = new URLSearchParams(window.location.search);
const user = urlParams.get('user');

// Fetch data and populate the UI of the page.
function buildUI() { // eslint-disable-line no-unused-vars
  fetchContent();
  window.onclick = (event) => {
    if (!event.target.matches('.reading-status-arrow') && !event.target.matches('.reading-status-arrow > *')) {
      const dropdowns = document.getElementsByClassName("dropdown");
      for (const dropdown of dropdowns) {
        dropdown.classList.add('hidden');
      }
    }
  }
}

// Fetch community and threads and add them to the page.
function fetchContent() {
  const url = `/user-book?user=${user}`;
  fetch(url)
    .then(response => response.json())
    .then((result) => {
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

function buildReadingStatusOption(userBook, readingStatus) {
  const button = document.createElement('button');
  button.classList.add('reading-status-option');
  button.classList.add(`reading-status-${userBook.status}`);
  button.innerText = readingStatusText[readingStatus];
  button.onclick = () => {
    $.ajax({
      type: 'POST',
      url: '/user-book',
      data: {
        bookId: userBook.bookId,
        status: readingStatus
      },
      success: () => {
        window.location = `/user-bookshelf.html?user=${userBook.user}`;
      }
    });
  };

  return button;
}

function buildUserBookDiv(userBook) {
  const titleDiv = document.createElement('h3');
  titleDiv.classList.add('book-title');
  titleDiv.innerText = userBook.book.title;

  const authorsDiv = document.createElement('div');
  authorsDiv.classList.add('book-authors');
  authorsDiv.innerText = userBook.book.authors.join();

  const readingStatusDiv = document.createElement('button');
  readingStatusDiv.classList.add('reading-status');
  readingStatusDiv.classList.add(`reading-status-${userBook.status}`);
  readingStatusDiv.innerText = readingStatusText[userBook.status];

  const downButton = document.createElement('button');
  downButton.classList.add('reading-status-arrow');
  downButton.classList.add(`reading-status-${userBook.status}`);
  downButton.innerHTML = '<i class="fa fa-chevron-down"></i>';
  downButton.onclick = () => {
    const dropdown = document.getElementById(`dropdown-${userBook.bookId}`)
    dropdown.classList.toggle('hidden');
  };

  const dropdown = document.createElement('div');
  dropdown.id = `dropdown-${userBook.bookId}`;
  dropdown.classList.add('dropdown');
  dropdown.classList.add('hidden');

  Object.keys(readingStatusText).forEach((readingStatus) => {
    if (userBook.status !== readingStatus) {
      dropdown.appendChild(buildReadingStatusOption(userBook, readingStatus));
    }
  });

  const bookDiv = document.createElement('div');
  bookDiv.classList.add('book-div');
  bookDiv.classList.add(`book-${userBook.status}`);
  bookDiv.appendChild(titleDiv);
  bookDiv.appendChild(authorsDiv);
  bookDiv.appendChild(readingStatusDiv);
  bookDiv.appendChild(downButton);
  bookDiv.appendChild(dropdown);

  return bookDiv;
}
