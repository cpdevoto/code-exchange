var wantify = wantify || {}

wantify.index = (function () {
  function getAccessToken () {
    var name = 'access-token'
    var value = '; ' + document.cookie
    var parts = value.split('; ' + name + '=')
    if (parts.length == 2) return parts.pop().split(';').shift()
  }

  function loadUsers (accessToken, promise) {
    $.ajax({
	  url: '/api/v1/users',
	  beforeSend: function (xhr) {
	    xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken)
	  }
    }).done(function (data) {
      promise(data)
    })
  }

  function renderUsers (data) {
    var user
    for (var i = 0; i < data.length; i++) {
      user = data[i]
      $('#users tr:last').after(
      '<tr>' +
      '<td>' + user.email + '</td>' +
      '<td>' + user.preferredUsername + '</td>' +
      '<td>' + user.name + '</td>' +
      '<td>' + user.givenName + '</td>' +
      '<td>' + user.familyName + '</td>' +
      '</tr>')
    }
  }

  function init () {
    var accessToken = getAccessToken()
    loadUsers(accessToken, renderUsers)
  }

  return {
    init: init
  }
})()

$(document).ready(function () {
  wantify.index.init()
})
