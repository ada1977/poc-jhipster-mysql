(function() {
    'use strict';

    angular
        .module('adaApp')
        .controller('PronoGameDialogController', PronoGameDialogController);

    PronoGameDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PronoGame', 'PronoLeague', 'PronoBet'];

    function PronoGameDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PronoGame, PronoLeague, PronoBet) {
        var vm = this;

        vm.pronoGame = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.pronoleagues = PronoLeague.query();
        vm.pronobets = PronoBet.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pronoGame.id !== null) {
                PronoGame.update(vm.pronoGame, onSaveSuccess, onSaveError);
            } else {
                PronoGame.save(vm.pronoGame, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('adaApp:pronoGameUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
