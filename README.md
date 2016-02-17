# alexarank

A trivial and intentionally unfinished JSON Web service for querying “Alexa top
1 million” domain list ranks.

## API

The API responds to requests of the form:

    GET /:domain
    
Where `:domain` is an FQDN in the E2LD to query.

Response is one of:

- 200, body `{"domain": String, "rank": Integer}`
- 404, body `{"domain": String, "rank": null}`
- [45]xx, body `{"error": String}`


## License

Copyright © 2016 Marshall Bockrath-Vandegrift & Contributors

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
