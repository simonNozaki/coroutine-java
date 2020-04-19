#!/bin/bash

# destroy an old container
./destroy_container.sh

# start a new container
./run_container.sh

# if following option is passed
if [ $0 == f ]; then
    docker logs -f javaee-servlet
fi