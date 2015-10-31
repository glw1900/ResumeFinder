require 'sinatra'
require 'sinatra/activerecord'
require './config/environments'
require './process'
require './models/applicant'        #Model class
require './models/company'        #Model class
require './models/project'        #Model class
require './models/receives'        #Model class
require './models/education'
require './models/experience'
require "pry-byebug"
enable :sessions

get '/' do
  erb :home
end

get "/app_register" do
  erb :regist_app
end

get "/com_register" do
  erb :regist_com
end

get "/person_page" do
  @user = session["username"]
  @p = Applicant.find_by(email: @user)
  id = @p.id
  @project = Project.where(appl_id:id)
  @experience = Experience.where(appl_id:id)
  @education = Education.where(appl_id:id)
  erb :person_page
end

get "/company_page" do
  @user = session["username"]
  @c = Company.find_by(email: @user)
  erb :company_page
end

post "/app_login" do
  @user = params[:user][:email]
  @pass = params[:user][:password]
  if auth(@user,@pass,"user")
    session["username"] = @user
    redirect '/person_page'
  else
    "Email and Password not match"
  end
end

post "/com_login" do
  @user = params[:company][:email]
  @pass = params[:company][:password]
  if auth(@user,@pass,"com")
    session["username"] = @user
    redirect '/company_page'
  else
    "Email and Password not match"
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


post "/request" do
  receive = {}
  receive[:appl_email] = params[:request][:appl_email]
  receive[:comp_email] = session["username"]
  @p = Applicant.find_by(email: params[:request][:appl_email])
  @r = Receive.new(receive)
  if @r.save
    erb :appl_page
  else
    "Something is Wrong"
  end
end
