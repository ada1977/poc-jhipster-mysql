(function() {
    'use strict';

    angular
        .module('adaApp')
        .controller('PronoLeagueDialogController', PronoLeagueDialogController);

    PronoLeagueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PronoLeague', 'PronoGame'];

    function PronoLeagueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PronoLeague, PronoGame) {
        var vm = this;

        vm.pronoLeague = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pronogames = PronoGame.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pronoLeague.id !== null) {
                PronoLeague.update(vm.pronoLeague, onSaveSuccess, onSaveError);
            } else {
                PronoLeague.save(vm.pronoLeague, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('adaApp:pronoLeagueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
