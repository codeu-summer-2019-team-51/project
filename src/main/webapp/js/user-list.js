/**
*Builds a list element that contains a link to a user page, e.g.
*<li><a href="/user-page.html?user=test@example.com">test@example.com</a></li>.
*/
function buildUserListItem(user) {
  const profilePic = document.createElement('img');
  let filePath = user.profilePic;
  if (!filePath) {
    filePath = 'image/profile-pic.png';
  }
  profilePic.src = filePath;
  profilePic.classList.add('profile-pic');

  const userLink = document.createElement('a');
  userLink.href = `/user-page.html?user=${user.email}`;
  userLink.appendChild(document.createTextNode(user.email));
  userLink.classList.add('user-link');

  const userAboutMe = document.createElement('p');
  userAboutMe.innerText = user.aboutMe;
  userAboutMe.classList.add('about-me');

  const userDiv = document.createElement('div');
  userDiv.classList.add('user-div');
  userDiv.appendChild(userLink);
  userDiv.appendChild(userAboutMe);

  const userListItem = document.createElement('li');
  userListItem.classList.add('user');
  userListItem.appendChild(profilePic);
  userListItem.appendChild(userDiv);
  return userListItem;
}

/**
*Fetches users and adds them to the page.
*/
function fetchUserList() {
  const url = '/users';
  fetch(url).then(response => response.json())
    .then((users) => {
      const list = document.getElementById('user-container');
      list.innerHTML = '';
      users.forEach((user) => {
        const userListItem = buildUserListItem(user);
        list.appendChild(userListItem);
      });
    });
}

/**
*Fetches data and populates the UI of the page.
*/
function buildUI() { // eslint-disable-line no-unused-vars
  fetchUserList();
}
