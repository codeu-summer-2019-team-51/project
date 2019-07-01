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

// Get ?user=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
console.log(urlParams);
const parameterUsername = urlParams.get('user');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterUsername) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter username. */
function setPageTitle() {
  document.getElementById('page-title').innerText = parameterUsername;
  document.title = parameterUsername + ' - User Page';
}

/**
 * Builds an element that displays the message.
 * @param {Message} message
 * @return {Element}
 */
function buildMessageDiv(message) {
  const headerDiv = document.createElement('div');
  const messageDate = new Date(message.timestamp);
  headerDiv.classList.add('message-header');
  headerDiv.appendChild(
    document.createTextNode(`${message.user} - ${messageDate}`)
  );

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('message-body');
  bodyDiv.innerHTML = message.text;

  const messageDiv = document.createElement('div');
  messageDiv.classList.add('message-div');
  messageDiv.appendChild(headerDiv);
  messageDiv.appendChild(bodyDiv);

  return messageDiv;
}

/**
 * Shows html element with specified elementId.
 */
function show(elementId) {
  const element = document.getElementById(elementId);
  element.classList.remove('hidden');
}

/**
 * Hides html element with specified elementId.
 */
function hide(elementId) { // eslint-disable-line no-unused-vars
  const element = document.getElementById(elementId);
  element.classList.add('hidden');
}

/**
 * Shows the message form if the user is logged in and viewing their own page.
 */
function showMessageFormIfViewingSelf() {
  fetch('/login-status')
    .then(response => response.json())
    .then((loginStatus) => {
      if (loginStatus.isLoggedIn
          && loginStatus.username === parameterUsername) {
        show('message-form');
        show('profile-pic-editor');
        show('about-me-editor');
      }
    });
}

/** Fetches messages and adds them to the page. */
function fetchMessages() {
  const url = `/messages?user=${parameterUsername}`;
  fetch(url)
    .then(response => response.json())
    .then((messages) => {
      const messagesContainer = document.getElementById('message-container');
      if (messages.length === 0) {
        messagesContainer.innerHTML = '<p>This user has no posts yet.</p>';
      } else {
        messagesContainer.innerHTML = '';
      }
      messages.forEach((message) => {
        const messageDiv = buildMessageDiv(message);
        messagesContainer.appendChild(messageDiv);
      });
    });
}

/** Fetches about me data and adds them to the page. */
function fetchAboutMe() {
  const url = `/about?user=${parameterUsername}`;
  fetch(url)
    .then(response => response.text())
    .then((response) => {
      const aboutMeContainer = document.getElementById('about-me-container');
      let aboutMe = response;
      if (response === '') {
        aboutMe = 'This user has not entered any information yet.';
      }
      aboutMeContainer.innerHTML = aboutMe;
    });
}

/** Fetches profile picture and adds it to the page. */
function fetchProfilePic() {
  const url = `/profile-pic?user=${parameterUsername}`;
  fetch(url)
    .then(response => response.text())
    .then((response) => {
      const profilePic = document.getElementById('profile-pic');
      let filePath = response;
      if (response === '') {
        filePath = 'image/profile-pic.png';
      }
      profilePic.src = filePath;
    });
}

/** Fetches data and populates the UI of the page. */
function buildUI() {
  setPageTitle();
  showMessageFormIfViewingSelf();
  fetchProfilePic();
  fetchAboutMe();
  fetchMessages();
}
