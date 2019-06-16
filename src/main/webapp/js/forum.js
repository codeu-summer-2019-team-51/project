function buildCommunityDiv(community) {
  const nameDiv = document.createElement('div');
  nameDiv.classList.add('left-align');
  nameDiv.classList.add('community-name');
  nameDiv.appendChild(document.createTextNode(community.name));

  const descriptionDiv = document.createElement('div');
  descriptionDiv.classList.add('left-align');
  descriptionDiv.classList.add('community-description');
  descriptionDiv.appendChild(document.createTextNode(community.description));

  const communityDiv = document.createElement('div');
  communityDiv.classList.add('community-div');
  communityDiv.appendChild(nameDiv);
  communityDiv.appendChild(descriptionDiv);

  return communityDiv;
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
        const communityDiv = buildCommunityDiv(community);
        communityContainer.appendChild(communityDiv);
      });
    });
}

// Fetch data and populate the UI of the page.
function buildUI() { // eslint-disable-line no-unused-vars
  fetchCommunities();
}
