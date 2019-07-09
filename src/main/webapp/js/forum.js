function buildCommunityDiv(community) {
  const communityLink = document.createElement('a');
  communityLink.href = `/community.html?id=${community.id}`;

  const nameDiv = document.createElement('h3');
  nameDiv.classList.add('left-align');
  nameDiv.classList.add('community-name');
  nameDiv.appendChild(document.createTextNode(community.name));

  communityLink.appendChild(nameDiv);

  const memberCountDiv = document.createElement('div');
  memberCountDiv.classList.add('left-align');
  memberCountDiv.classList.add('community-member-count');
  let memberCount = community.members.length;
  memberCount = memberCount > 1 ? `${memberCount} members`
                                : `${memberCount} member`;
  memberCountDiv.appendChild(document.createTextNode(memberCount));

  const descriptionDiv = document.createElement('div');
  descriptionDiv.classList.add('left-align');
  descriptionDiv.classList.add('community-description');
  descriptionDiv.appendChild(document.createTextNode(community.description));

  const joinButton = document.createElement('button');
  joinButton.classList.add('community-join');
  joinButton.innerText = 'Join';
  joinButton.onclick = () => {
    $.ajax({
      type: 'POST',
      url: '/join-community',
      data: {
        communityId: community.id
      },
      success: () => {
        window.location = `/forum.html`;
      }
    });
  };

  const communityA = document.createElement('a');
  communityA.classList.add('community-a');
  communityA.appendChild(communityLink);
  communityA.appendChild(memberCountDiv);
  communityA.appendChild(descriptionDiv);
  communityA.appendChild(joinButton);

  return communityA;
}

// Fetch communities and add them to the page.
function fetchCommunities() {
  const url = '/forum';
  return fetch(url).then(response => response.json())
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

// Disable 'create new community button' if the user is not logged in
function disableIfNotLoggedIn() {
  fetch('/login-status')
    .then(response => response.json())
    .then((loginStatus) => {
      if (!loginStatus.isLoggedIn) {
        const createButton = document.getElementById('create-button');
        createButton.classList.add('disabled');
        createButton.href = null;
        createButton.onclick = () => {
          window.location = '/login';
        }

        const buttons = document.getElementsByTagName('button');
        for (const button of buttons) {
          button.classList.add('disabled');
          button.onclick = () => {
            window.location = '/login';
          }
        }
      }
    });
}

// Fetch data and populate the UI of the page.
function buildUI() { // eslint-disable-line no-unused-vars
  fetchCommunities()
    .then(() => disableIfNotLoggedIn());
}
