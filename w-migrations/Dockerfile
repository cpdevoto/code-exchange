# Base our image on an official, minimal image of our preferred Ruby
FROM ruby:2.3.3

# Install essential Linux packages
RUN apt-get update -qq && apt-get install -y build-essential libpq-dev git postgresql-client-9.4

# Define where our application will live inside the image
ENV RAILS_ROOT /var/www/migrations-app

WORKDIR $RAILS_ROOT

COPY Gemfile Gemfile
COPY Gemfile.lock Gemfile.lock
RUN gem install bundler
RUN gem install libv8 -- --with-system-v8
RUN bundle config build.nokogiri --use-system-libraries
RUN gem install nokogiri -v 1.7.0.1 -- --use-system-libraries --with-xml2-include=/usr/local/opt/libxml2/include/libxml2
RUN gem install pg -v '0.19.0' -- --use-system-libraries --with-xml2-include=/usr/local/opt/libxml2/include/libxml2
RUN bundle install
COPY . .

