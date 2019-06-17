function buildCommunityDiv(community) {
  const nameDiv = document.createElement('div');
  nameDiv.classList.add('left-align');
  nameDiv.classList.add('community-name');
  nameDiv.appendChild(document.createTextNode(community.name));

  const descriptionDiv = document.createElement('div');
  descriptionDiv.classList.add('left-align');
  descriptionDiv.classList.add('community-description');
  descriptionDiv.appendChild(document.createTextNode(community.description));

  const communityA = document.createElement('a');
  communityA.classList.add('community-a');
  communityA.appendChild(nameDiv);
  communityA.appendChild(descriptionDiv);
  communityA.setAttribute('href', `/community.html?id=${community.id}`);

  return communityA;
}

// Fetch communities and add them to the page.
function fetchCommunities() {
  const url = '/forum';
  fetch(url).then(response => response.json())
    .then((communities) => {
      const communityContainer = document.getElementById('community-container');
      if (communities.length === 0) {
        communityContainer.innerHTML = '<p>There are no communities yet.</p>';
      } else {
        communityContainer.innerHTML = '';
      }
      communities.forEach((community) => {
        const communityA = buildCommunityDiv(community);
        communityContainer.appendChild(communityA);
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

// Fetch data and populate the UI of the page.
function buildUI() { // eslint-disable-line no-unused-vars
  showIfLoggedIn();
  fetchCommunities();
}
