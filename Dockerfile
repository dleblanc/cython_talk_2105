FROM ipython/scipystack

# Install SBT
RUN apt-key net-update; \
    apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 379CE192D401AB61 && \
    echo "deb http://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list && \
    apt-get update && \
    apt-get install -y --force-yes sbt


# Copy over all the testing files to the container
COPY * ./

RUN python setup.py clean build

RUN useradd -ms /bin/bash developer

RUN chgrp developer -R *

RUN chmod g+w -R *

USER developer

CMD python time_compare.py
