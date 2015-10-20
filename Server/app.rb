require 'sinatra'
require 'sinatra/activerecord'
require './config/environments'
require './process'
require './models/applicants'        #Model class
require './models/companies'        #Model class
require './models/projects'        #Model class
require './models/receives'        #Model class
# require "pry-byebug"

get '/' do
  erb :home
end

get "/app_register" do
  erb :regist_app
end

get "/com_register" do
  erb :regist_com
end

post "/app_login" do
  @user = params[:user][:email]
  @pass = params[:user][:password]
  if auth(@user,@pass,"user")
    session[]
  end
end

post "/com_login" do
  @user = params[:user][:email]
  @pass = params[:user][:password]
  if auth(@user,@pass,"com")

  end
end

post "/submit_regis_app" do
  var = check_pwd(params[:regis])
  if var.nil?
    redirect "/app_register"
  else
    @company = Applicant.new(var)
    if @company.save
      redirect '/'
    else
      "Something is Wrong"
    end
  end
end

post "/submit_regis_com" do
  var = check_pwd(params[:regis])
  if var.nil?
    redirect "/com_register"
  else
    @company = Company.new(var)
    if @company.save
      redirect '/'
    else
      "Something is Wrong"
    end
  end
end

def auth(user,pass,type)
  if type == "user"
    u = Applicant.find_by(email: user)
  else
    u = Company.find_by(email: user)
  end
  if u.nil?
    return false
  end
  if u.password == pass
    return true
  end
  return false
end
