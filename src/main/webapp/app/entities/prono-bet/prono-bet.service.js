(function() {
    'use strict';
    angular
        .module('adaApp')
        .factory('PronoBet', PronoBet);

    PronoBet.$inject = ['$resource'];

    function PronoBet ($resource) {
        var resourceUrl =  'api/prono-bets/:id';

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
