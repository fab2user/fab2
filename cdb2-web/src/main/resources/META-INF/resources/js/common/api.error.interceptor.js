(function () {
  'use strict';

  angular.module('cdb2').factory('apiErrorInterceptor', apiErrorInterceptor);

  apiErrorInterceptor.$inject = ['$q', '$injector'];

  function apiErrorInterceptor($q, $injector) {
    var handleResponseError = function (rejectReason) {
      var toastr = $injector.get('toastr');
      var $translate = $injector.get('$translate');
      var AuthService = $injector.get('AuthService');
      var state = $injector.get('$state');

      if (rejectReason.status === 403 || rejectReason.status === 401) {
        if (state.current.name === 'root.login') {
          AuthService.deleteUserDataWithoutReload();
        } else {
          // Make sure we update app's status when server revokes user
          AuthService.deleteUserData();
        }
      } else {
        // Errors from server will be displayed to user
        if (!rejectReason.config.params ||
          rejectReason.config.params.suppressErrors !== true
        ) {
          if (rejectReason.data && rejectReason.data.message) {
            toastr.error($translate.instant(rejectReason.data.message));
          }
        }

        // Error in case server is not available
        if (rejectReason.status === -1) {
          toastr.error($translate.instant('global.error.serverunavailable'));
        }

        // When angularjs requests for a file (type bufferarray), resonseType stays bufferarray even when response is in error.
        if (rejectReason.config.responseType === 'arraybuffer') {
          var decodedString = String.fromCharCode.apply(null, new Uint8Array(rejectReason.data));
          var responseObj = JSON.parse(decodedString);
          toastr.error(responseObj.message);
        }
      }

      //WARNING ! Don't remove this line: if the response is in error and don't reject the promise, calling service will see it as success !!!
      return $q.reject(rejectReason);
    };

    return {
      responseError: handleResponseError,
    };
  }
})();