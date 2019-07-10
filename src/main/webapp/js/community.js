// Get ?id=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');

function buildThreadDiv(thread) {
  const nameDiv = document.createElement('h3');
  nameDiv.classList.add('left-align');
  nameDiv.classList.add('thread-name');
  nameDiv.appendChild(document.createTextNode(thread.name));

  const descriptionDiv = document.createElement('div');
  descriptionDiv.classList.add('left-align');
  descriptionDiv.classList.add('thread-description');
  descriptionDiv.appendChild(document.createTextNode(thread.description));

  const threadA = document.createElement('a');
  threadA.classList.add('thread-a');
  threadA.appendChild(nameDiv);
  threadA.appendChild(descriptionDiv);
  threadA.setAttribute('href', `/thread.html?id=${thread.id}`);

  return threadA;
}

function loadCommunityDetails(community) {
  const title = document.getElementsByTagName('title')[0];
  title.innerText = community.name;

  const header = document.getElementById('header');
  header.innerText = community.name;

  const description = document.getElementById('description');
  description.innerText = community.description;

  const members = document.getElementById('members');
  const memberCount = community.members.length;
  members.innerHTML = memberCount > 1
    ? `<b>${memberCount} members</b>`
    : `<b>${memberCount} member</b>`;
}

function loadThreads(threads) {
  const threadContainer = document.getElementById('thread-container');
  if (threads.length === 0) {
    threadContainer.innerHTML = '<p>There are no threads yet.</p>';
  } else {
    threadContainer.innerHTML = '';
  }
  threads.forEach((thread) => {
    const threadA = buildThreadDiv(thread);
    threadContainer.appendChild(threadA);
  });
}

// Fetch community and threads and add them to the page.
function fetchContent() {
  const url = `/community?id=${id}`;
  return fetch(url).then(response => response.json())
    .then((result) => {
      loadCommunityDetails(result.community);
      loadThreads(result.threads);
      return result;
    });
}

// Disable 'create new thread' button if the user is not logged in or not a
// member of the community
function disableIfUnauthorized(community) {
  fetch('/login-status')
    .then(response => response.json())
    .then((loginStatus) => {
      if (!loginStatus.isLoggedIn
          || community.members.indexOf(loginStatus.username) == -1) {
        const createButton = document.getElementById('create-button');
        createButton.classList.add('disabled');
        createButton.href = null;
        if (!loginStatus.isLoggedIn) {
          createButton.onclick = () => {
            window.location = '/login';
          }
        } else {
          createButton.onclick = () => {
            alert(`You need to have joined community <b>${community.name}</b>`
                + ` to create a new thread`);
          }
        }
      }
    });
}

function setPostParam() {
  const communityIdParam = document.getElementById('community-id-param');
  communityIdParam.setAttribute('value', id);
}

// Fetch data and populate the UI of the page.
function buildUI() { // eslint-disable-line no-unused-vars
  fetchContent()
    .then(result => disableIfUnauthorized(result.community));
  setPostParam();
}
