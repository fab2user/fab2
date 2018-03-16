(function() {
  'use strict';

  angular
    .module('cdb2')
    .constant('SERVER', {
      ROOT: '',
      API: 'api'
    })
    .constant('STORE', {
      USER: 'connected_user',
      AUTHENTICATED: 'authenticated'
    })
    .constant('EVENT', {
      LOGGED_IN: 1,
      XML_IMPORT: 'xml_import',
      GEONAME_UPDATE: 'geoname_update'
    })
    .constant('STATUS', {
      IN_PROGRESS: 'IN_PROGRESS',
      OK: 'OK',
      ERROR: 'ERROR'
    });
})();
