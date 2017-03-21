(function() {
    'use strict';
    angular
        .module('adaApp')
        .factory('Prono', Prono);

    Prono.$inject = ['$resource'];

    function Prono ($resource) {
        var resourceUrl =  'api/prono-games/';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
