// Get ?id=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');

function loadThreadDetails(thread) {
  const title = document.getElementsByTagName('title')[0];
  title.innerText = thread.name;

  const header = document.getElementById('header');
  header.innerText = thread.name;

  const description = document.getElementById('description');
  description.innerText = thread.description;

  const creator = document.getElementById('creator');
  creator.innerText = thread.creator;

  const threadIdInput = document.getElementById('thread-id-input');
  threadIdInput.value = thread.id;
}

function buildReplyForm(comment) {
  const replyForm = document.createElement('form');
  replyForm.action = '/thread';
  replyForm.method = 'POST';

  const textArea = document.createElement('textarea');
  textArea.name = 'text';
  textArea.placeholder = 'Enter your comment';
  textArea.rows = '4';
  textArea.required = true;

  const parentIdInput = document.createElement('input');
  parentIdInput.type = 'hidden';
  parentIdInput.name = 'parentId';
  parentIdInput.value = comment.id;

  const threadIdInput = document.createElement('input');
  threadIdInput.type = 'hidden';
  threadIdInput.name = 'threadId';
  threadIdInput.value = comment.threadId;

  const submitButton = document.createElement('input');
  submitButton.type = 'submit';
  submitButton.value = 'Reply';
  submitButton.classList.add('button');

  const cancelButton = document.createElement('button');
  cancelButton.classList.add("button-outline");
  cancelButton.innerText = "Cancel";
  cancelButton.onclick = () => {
    document.getElementById(`reply-${comment.id}`).classList.add('hidden');
  };

  replyForm.appendChild(textArea);
  replyForm.appendChild(parentIdInput);
  replyForm.appendChild(threadIdInput);
  replyForm.appendChild(submitButton);
  replyForm.appendChild(cancelButton);

  return replyForm;
}

function buildCommentDiv(comment) {
  let date = new Date(comment.timestamp)
  const options = {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: 'numeric',
    minute: '2-digit'
  };
  date = date.toLocaleDateString('default', options);

  const headerDiv = document.createElement('div');
  headerDiv.classList.add('comment-header');
  headerDiv.innerHTML = `<b>${comment.user}</b> ${date}`;

  const textDiv = document.createElement('div');
  textDiv.classList.add('comment-text');
  textDiv.appendChild(document.createTextNode(comment.text));

  const replyButton = document.createElement('button');
  replyButton.classList.add("button-clear");
  replyButton.classList.add("reply-button");
  replyButton.innerText = "Reply";
  replyButton.onclick = () => {
    document.getElementById(`reply-${comment.id}`).classList.remove('hidden');
  };

  const replyForm = buildReplyForm(comment);
  replyForm.id = `reply-${comment.id}`;
  replyForm.classList.add('hidden');

  const commentDiv = document.createElement('div');
  commentDiv.classList.add('comment-div');
  commentDiv.appendChild(headerDiv);
  commentDiv.appendChild(textDiv);
  commentDiv.appendChild(replyButton);
  commentDiv.appendChild(replyForm);

  return commentDiv;
}

function buildCommentTree(comment) {
  const commentTree = document.createElement('div');
  commentTree.classList.add('comment-tree');

  const commentDiv = buildCommentDiv(comment.content);
  commentTree.appendChild(commentDiv);

  const commentSubcontainer = document.createElement('div');
  commentSubcontainer.classList.add('container');
  comment.children.forEach((comment) => {
    const commentSubtree = buildCommentTree(comment);
    commentSubcontainer.appendChild(commentSubtree);
  });
  commentTree.appendChild(commentSubcontainer);

  return commentTree;
}

function loadComments(comments) {
  const commentContainer = document.getElementById('comment-container');
  if (comments.length === 0) {
    commentContainer.innerHTML = '<p>There are no comments yet.</p>';
  } else {
    commentContainer.innerHTML = '';
  }

  comments.forEach((comment) => {
    const commentTree = buildCommentTree(comment);
    commentContainer.appendChild(commentTree);
  });
}

// Fetch thread and comments and add them to the page.
function fetchContent() {
  const url = `/thread?id=${id}`;
  return fetch(url).then(response => response.json())
    .then((result) => {
      loadThreadDetails(result.thread);
      loadComments(result.comments);
      return result;
    });
}


// Disable 'reply' buttons if the user is not logged in or not a
// member of the community
function disableIfUnauthorized(thread) {
  const loginStatus = fetch('/login-status').then(response => response.json());
  const communityData = fetch(`/community?id=${thread.communityId}`)
      .then(response => response.json());
  // TODO: create another servlet query to fetch only community data without its
  // threads for optimization

  Promise.all([loginStatus, communityData])
    .then((response) => {
      const [loginStatus, communityData] = response;
      const community = communityData.community;
      if (!loginStatus.isLoggedIn
          || community.members.indexOf(loginStatus.username) == -1) {
        const submitButtons =
            Array.from(document.getElementsByClassName('button'));
        const buttons = Array.from(document.getElementsByTagName('button'));
        buttons.concat(submitButtons).forEach((button) => {
          button.classList.add('disabled');
          button.href = null;
          if (!loginStatus.isLoggedIn) {
            button.onclick = () => {
              window.location = '/login';
            }
          } else {
            button.onclick = () => {
              alert(`You need to have joined community `
                  + `${community.name} to reply to this thread`);
            }
          }
        });
      }
    });
}

// Fetch data and populate the UI of the page.
function buildUI() { // eslint-disable-line no-unused-vars
  fetchContent()
    .then((result) => disableIfUnauthorized(result.thread));
}
