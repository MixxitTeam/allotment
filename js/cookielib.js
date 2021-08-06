(function() {
  var cookieListeners = [];
  var cookie = {
    name: "cookiestate",
    value: "e41f26a6ea7941edb2c8c53e40a47f8f",
    days: 3650
  };
  var cookieMessageRoot = null;

  function getCookie(name) {
    var dc = document.cookie;
    var prefix = name + "=";
    var begin = dc.indexOf("; " + prefix);
    if (begin === -1) {
      begin = dc.indexOf(prefix);
      if (begin !== 0)
        return null;
    } else {
      begin += 2;
      var end = document.cookie.indexOf(";", begin);
      if (end === -1) {
        end = dc.length;
      }
    }
    return decodeURI(dc.substring(begin + prefix.length, end));
  }

  function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
  }

  function deleteCookie(cname) {
    setCookie(cname, "", -3650);
  }

  function setHasAcceptedCookies(accepted) {
    if (accepted)
      setCookie(cookie.name, cookie.value, cookie.days);
    else
      deleteCookie(cookie.name);
    
    for (var i = 0; i < cookieListeners.length; ++i)
      cookieListeners[i](accepted);
  }

  function makeElementWithClass(tagName, className) {
    var elem = document.createElement(tagName);
    elem.className = className;
    return elem;
  }

  function createCookieMessage() {
    if (cookieMessageRoot !== null)
      return;
    
    cookieMessageRoot = makeElementWithClass("div", "cookiemsg-root");
    var cookieMessageInner = makeElementWithClass("div", "cookiemsg-inner");
    var cookieMessageText = makeElementWithClass("div", "cookiemsg-text");
    var cookieMessageButtons = makeElementWithClass("div", "cookiemsg-buttons");
    var cookieMessageAccept = makeElementWithClass("button", "cookiemsg-accept");
    var cookieMessageDecline = makeElementWithClass("button", "cookiemsg-decline");

    cookieMessageText.innerText = "This feature requires cookies to be set. These cookies are soley functional and are NEVER use for tracking, targeting or identification.";
    cookieMessageAccept.innerText = "Accept";
    cookieMessageDecline.innerText = "Decline";

    cookieMessageAccept.addEventListener("click", function() {
      setHasAcceptedCookies(true);
      hideCookieMsg();
    });

    cookieMessageDecline.addEventListener("click", function() {
      setHasAcceptedCookies(false);
      hideCookieMsg();
    });

    cookieMessageButtons.appendChild(cookieMessageAccept);
    cookieMessageButtons.appendChild(cookieMessageDecline);
    cookieMessageInner.appendChild(cookieMessageText);
    cookieMessageInner.appendChild(cookieMessageButtons);
    cookieMessageRoot.appendChild(cookieMessageInner);
  }

  window.showCookieMsg = function() {
    createCookieMessage();
    document.body.appendChild(cookieMessageRoot);
  };

  function hideCookieMsg() {
    document.body.removeChild(cookieMessageRoot);
  };

  window.getCookie = function(name) {
    return getCookie(name);
  };
  window.setCookie = function(name, value, days) {
    return setCookie(name, value, days);
  };
  window.getHasAcceptedCookies = function() {
    return getCookie(cookie.name) === cookie.value;
  };
  window.cookieMessageRemoveListener = function(listener) {
    var index = cookieListeners.indexOf(listener);
    if (index < 0)
      return;
    cookieListeners.splice(index, 1);
  };
  window.cookieMessageAddListener = function(listener) {
    if (cookieListeners.indexOf(listener) >= 0)
      return;

    cookieListeners.push(listener);
  };
}());
