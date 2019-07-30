/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

function loadNavigation() {  // eslint-disable-line no-unused-vars
  const navigation = document.getElementsByTagName('nav')[0];
  const navBar = document.createElement('ul');
  navigation.appendChild(navBar);

  navBar.appendChild(createNavItem('Home', '/'));
  navBar.appendChild(createNavItem('Books', '/book-list.html'));
  navBar.appendChild(createNavItem('Reviews', '/review-feed.html'));
  navBar.appendChild(createNavItem('Forum', '/forum.html'));
  navBar.appendChild(createNavItem('User List', '/user-list.html'));
  navBar.appendChild(createNavItem('About Us', '/aboutus.html'));

  fetch('/login-status')
    .then(response => response.json())
    .then((loginStatus) => {
      if (loginStatus.isLoggedIn) {
        navBar.appendChild(createNavItem('Your Page', `/user-page.html?user=${loginStatus.username}`));
        navBar.appendChild(createNavItem('Logout', '/logout'));

        // Set bookshelf hyperlink in project description in home page
        const linkToBookshelf = document.getElementById('user-bookshelf');
        if (linkToBookshelf) {
          linkToBookshelf.href = `/user-bookshelf.html?user=${loginStatus.username}`;
        }
      } else {
        navBar.appendChild(createNavItem('Login', '/login'));
      }
    });
}

/**
 * Creates a link in a navigation bar made of a li element with an anchor element
 * as its child.
 * @param {string} url
 * @param {string} text
 * @return {Element} li element
 */
function createNavItem(text, url) {
  const linkElement = document.createElement('a');
  linkElement.appendChild(document.createTextNode(text));
  linkElement.href = url;

  const listItemElement = document.createElement('li');
  listItemElement.appendChild(linkElement);
  return listItemElement;
}
