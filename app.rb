require 'sinatra'
require 'sinatra/activerecord'
require './config/environments' # database configuration

get '/' do
  erb :home
end

get '/register' do
  
end

post "/submit_regis_app" do
  @applicant = Applicant.new(params[:regis])
  if @applicant.save:
    redirect '/'
  else:
    "Something is Wrong"
  end
end