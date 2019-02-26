(function () {
  'use strict';

  angular
    .module('cdb2')
    .controller('BailiffEditController', BailiffEditController);

  BailiffEditController.$inject = [
    '$log',
    '$translate',
    '$uibModalInstance',
    '$uibModal',
    'lodash',
    'toastr',
    'NgTableParams',
    'BailiffAPIService',
    'BailiffCompAreaAPIService',
    'InstrumentAPIService',
    'LangAPIService',
    'bailiff',
    'cities',
    'competences',
    'areas',
    'nationalIdPrefix'
  ];

  function BailiffEditController(
    $log,
    $translate,
    $uibModalInstance,
    $uibModal,
    lodash,
    toastr,
    NgTableParams,
    BailiffAPIService,
    BailiffCompAreaAPIService,
    InstrumentAPIService,
    LangAPIService,
    bailiff,
    cities,
    competences,
    areas,
    nationalIdPrefix
  ) {
    var vm = this;
    vm.modalInstance = $uibModalInstance;
    vm.bailiff = bailiff;
    // vm.instrumentsOpt = [];
    vm.competences = competences;
    vm.nationalIdPrefix = nationalIdPrefix;

    var snapshotCompare = bailiff.id ? {
        instrumentIds: bailiff.instrumentIds.slice(0),
      geo: Object.assign({}, bailiff.geo)
    } : {
      competences: {},
      geo: {}
    };

    vm.areas = areas;

    LangAPIService.getAll().$promise.then(function (data) {
      vm.languages = data;
    });

    vm.getLanguages = function () {
      return vm.bailiff.languages;
    };

    vm.check = function (value, checked) {
      var idx = vm.bailiff.languages.indexOf(value);
      if (idx >= 0 && !checked) {
        vm.bailiff.languages.splice(idx, 1);
      }
      if (idx < 0 && checked) {
        vm.bailiff.languages.push(value);
      }
    };

    vm.delete = function () {
      BailiffAPIService.delete({
        id: vm.bailiff.id
      }).$promise.then(
        function () {
          toastr.success($translate.instant('global.toastr.delete.success'));
          vm.modalInstance.close();
        }
      );
    };

    // Only purpose of initialCity is to display correct municipality in angucomplete when editing existing bailiff
    vm.bailiff.initialCity = {
      postalCode: vm.bailiff.postalCode,
      name: vm.bailiff.city
    };
    vm.cities = cities;
    vm.errorsFromServer = null;

    if (vm.bailiff.id) {
      loadBailiffCompArea();
    }

    vm.save = function (isValid) {
      vm.errorsFromServer = null;
      if (isValid) {
        vm.submitted = true;
        var serializedBailiff = serializeBailiff();

        BailiffAPIService.save({}, serializedBailiff)
          .$promise.then(function (data) {
            vm.bailiff.id = data.id;
            toastr.success($translate.instant('global.toastr.save.success'));
            vm.modalInstance.close();
          })
          .catch(function (err) {
            $log.error(err);
            vm.errorsFromServer = $translate.instant(err.data.message);
          })
          .finally(function () {
            vm.submitted = false;
          });
      }
    };

    function serializeBailiff() {
      var bailiff = Object.assign({}, vm.bailiff);
      if (vm.bailiff.municipality.originalObject.id) {
        bailiff.municipalityId = vm.bailiff.municipality.originalObject.id;
      }
      bailiff.municipality = vm.bailiff.municipality.originalObject.name;
      if (
        lodash.isEqual(snapshotCompare.instrumentIds, bailiff.instrumentIds) &&
        lodash.isEqual(snapshotCompare.geo, bailiff.geo)
      ) {
        bailiff.toBeUpdated = false;
      } else {
        bailiff.toBeUpdated = true;
      }
      $log.info(
        'comp eq: ',
        lodash.isEqual(snapshotCompare.instrumentIds, bailiff.instrumentIds)
      );
      $log.info('geo eq: ', lodash.isEqual(snapshotCompare.geo, bailiff.geo));
      
      return bailiff;
    }

    function loadBailiffCompArea() {
      BailiffCompAreaAPIService.getAllForBailiff({
        bailiffId: vm.bailiff.id
      }).$promise.then(function (success) {
        var rs = lodash.map(success, function (comp) {
          var fd = lodash.find(vm.competences, function (c) {
            return c.instrumentId === comp.competence.instrument.id;
          });
          $log.info('Objet trouve: ', fd);
          return fd;
        });
        // vm.instrumentsOpt = Object.assign({}, rs);
        //Build area names list, to be displayed in smart table
        var model = success;
        model = buildAreasList(model);
        vm.tableParams = new NgTableParams({}, {
          dataset: model
        });
      });
    }

    vm.addCompetence = function () {
      newEdit({
        areas: []
      });
    };

    vm.editCompetence = function (competence) {
      newEdit(competence);
    };

    vm.removeCompetence = function (competence) {
      vm.errorsFromServer = null;
      vm.submitted = true;

      BailiffCompAreaAPIService.delete({
          id: competence.id
        })
        .$promise.then(function () {
          loadBailiffCompArea();
          toastr.success($translate.instant('global.toastr.delete.success'));
        })
        .catch(function (err) {
          $log.error(err);
          vm.errorsFromServer = $translate.instant(err.data.message);
        })
        .finally(function () {
          vm.submitted = false;
        });
    };

    function buildAreasList(model) {
      return lodash.map(model, function (record) {
        var areaNames = lodash.map(record.areas, 'name');
        record.areaNames = areaNames.join(', ');
        return record;
      });
    }

    function newEdit(competence) {
      var modalInstance = $uibModal.open({
        templateUrl: '/js/bailiff/competence.edit.html',
        controller: 'CompetenceEditController as competenceEditCtrl',
        windowClass: 'modal-hg',
        backdrop: 'static',
        resolve: {
          bailiff: vm.bailiff,
          bailiffCompArea: competence
        }
      });

      modalInstance.result.then(function () {
        loadBailiffCompArea();
      });
    }
  }
})();