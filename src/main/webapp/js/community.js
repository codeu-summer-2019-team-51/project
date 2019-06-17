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
function fetchContent(id) {
  const url = `/community?id=${id}`;
  fetch(url).then(response => response.json())
    .then((result) => {
      loadCommunityDetails(result.community);
      loadThreads(result.threads);
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
  fetchContent(id);
  setPostParam(id);
}
