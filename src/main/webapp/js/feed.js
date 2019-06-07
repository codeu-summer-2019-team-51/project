function buildMessageDiv(message) {
  const usernameDiv = document.createElement('div');
  usernameDiv.classList.add('message-header');
  usernameDiv.classList.add('column');
  usernameDiv.appendChild(document.createTextNode(message.user));

  const timeDiv = document.createElement('div');
  timeDiv.classList.add('message-header');
  timeDiv.classList.add('column');
  timeDiv.classList.add('right-align');
  timeDiv.appendChild(document.createTextNode(new Date(message.timestamp)));

  const headerRow = document.createElement('div');
  headerRow.classList.add('row');
  headerRow.appendChild(usernameDiv);
  headerRow.appendChild(timeDiv);

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('message-body');
  bodyDiv.innerHTML = message.text;

  const bodyColumn = document.createElement('div');
  bodyColumn.classList.add('column');
  bodyColumn.appendChild(bodyDiv);

  const bodyRow = document.createElement('div');
  bodyRow.classList.add('row');
  bodyRow.appendChild(bodyColumn);

  const messageDiv = document.createElement('div');
  messageDiv.classList.add('container');
  messageDiv.classList.add('message-div');
  messageDiv.appendChild(headerRow);
  messageDiv.appendChild(bodyRow);

  return messageDiv;
}

// Fetch messages and add them to the page.
function fetchMessages() {
  const url = '/feed';
  fetch(url).then(response => response.json())
    .then((messages) => {
      const messageContainer = document.getElementById('message-container');
      if (messages.length === 0) {
        messageContainer.innerHTML = '<p>There are no posts yet.</p>';
      } else {
        messageContainer.innerHTML = '';
      }
      messages.forEach((message) => {
        const messageDiv = buildMessageDiv(message);
        messageContainer.appendChild(messageDiv);
      });
    });
}

// Fetch data and populate the UI of the page.
function buildUI() { // eslint-disable-line no-unused-vars
  fetchMessages();
}