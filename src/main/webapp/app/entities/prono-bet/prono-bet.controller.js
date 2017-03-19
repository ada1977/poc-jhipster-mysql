(function() {
    'use strict';

    angular
        .module('adaApp')
        .controller('PronoBetController', PronoBetController);

    PronoBetController.$inject = ['PronoBet'];

    function PronoBetController(PronoBet) {

        var vm = this;

        vm.pronoBets = [];

        loadAll();

        function loadAll() {
            PronoBet.query(function(result) {
                vm.pronoBets = result;
                vm.searchQuery = null;
            });
        }
    }
})();
