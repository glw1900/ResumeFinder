require 'sinatra'
require 'sinatra/activerecord'
require './config/environments'

get '/' do
  erb :home
end

get "/app_register" do
  erb :regis_app
end

get "/com_register" do
  erb :regis_com
end

post "/app_login" do
  
end

post "/com_login" do
  
end

post "/submit_regis_app" do
  @applicant = Applicant.new(params[:regis])
  if @applicant.save
    redirect '/'
  else
    "Something is Wrong"
  end
end

post "/submit_regis_com" do
  @company = Company.new(param[:regis])
  if @company.save
    redirect '/'
  else
    "Something is Wrong"
  end
end
