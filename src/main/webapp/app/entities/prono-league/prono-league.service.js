(function() {
    'use strict';
    angular
        .module('adaApp')
        .factory('PronoLeague', PronoLeague);

    PronoLeague.$inject = ['$resource'];

    function PronoLeague ($resource) {
        var resourceUrl =  'api/prono-leagues/:id';

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
