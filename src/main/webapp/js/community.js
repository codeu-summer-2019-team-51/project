function getCommunityId() {
  let params = window.location.search.substr(1);
  params = params.split("&");
  for (const element of params) {
    const param = element.split("=");
    if (param[0] = 'id') {
      return param[1];
    }
  }
  return '';
}

function buildThreadDiv(thread) {
  const nameDiv = document.createElement('div');
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

  return threadA;
}

// Fetch threads and add them to the page.
function fetchThreads(id) {
  const url = `/community?id=${id}`;
  fetch(url).then(response => response.json())
    .then((result) => {
      const threads = result.threads;
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
    });
}

/**
 * Shows if the user is logged in.
 */
function showIfLoggedIn() {
  fetch('/login-status')
    .then(response => response.json())
    .then((loginStatus) => {
      if (loginStatus.isLoggedIn) {
        document.getElementById('create-button').classList.remove('hidden');
      }
    });
}

function setPostParam(id) {
  const communityIdParam = document.getElementById('community-id-param');
  communityIdParam.setAttribute('value', id);
}

// Fetch data and populate the UI of the page.
function buildUI() { // eslint-disable-line no-unused-vars
  const id = getCommunityId();
  showIfLoggedIn();
  fetchThreads(id);
  setPostParam(id);
}
