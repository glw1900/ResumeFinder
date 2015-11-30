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
  @email = session["email"]
  @p = Applicant.find_by(email: @email)
  @project = Project.where(appl_email:@p.email)
  @experience = Experience.where(appl_email:@p.email)
  @education = Education.where(appl_email:@p.email)
  erb :person_page
end

get "/company_page" do
  @email = session["email"]
  @c = Company.find_by(email: @email)
  list = Receive.where(comp_email: @email)
  @appl_list = []
  list.each do |receive|
    em = receive.appl_email
    appl = Applicant.find_by(email: em)
    @appl_list.push(appl)
  end
  erb :company_page
end

get "/update_project" do
  erb :update_project
end

get "/update_experience" do
  erb :update_experience
end

get '/update_education' do
  erb :update_education
end

get '/appl/:appl_email' do
  appl_email = params[:appl_email]
  @p = Applicant.find_by(email: appl_email)
  erb :appl_page
end

post '/add_experience' do
  params[:experience][:appl_email] = session["email"]
  @experience = Experience.new(params[:experience])
  if @experience.save
    redirect "/person_page"
  else
    "Please Add Again"
  end
end

post '/add_education' do
  params[:education][:appl_email] = session["email"]
  @education = Education.new(params[:education])
  if @education.save
    redirect "/person_page"
  else
    "Please Add Again"
  end
end

post "/add_project" do
  params[:project][:appl_email] = session["email"]
  @project = Project.new(params[:project])
  if @project.save
    redirect "/person_page"
  else
    "Please Add Again"
  end
end

post "/app_login" do
  @email = params[:user][:email]
  @pass = params[:user][:password]
  if auth(@email,@pass,"user")
    session["email"] = @email
    redirect '/person_page'
  else
    "Email and Password not match"
  end
end

post "/com_login" do
  @email = params[:company][:email]
  @pass = params[:company][:password]
  if auth(@email,@pass,"com")
    session["email"] = @email
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
