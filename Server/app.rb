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
require 'json'
set :public_folder, File.dirname(__FILE__)+'/bootstrap-3.3.5-dist'
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
  @email = params[:user][:email]
  @pass = params[:user][:password]
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
  receive[:comp_email] = session["email"]
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

#api
get "/api/appl_login/:var" do
  result = parse(params[:var])
  @email = result["appl_email"]
  @pass = result["password"]
  if auth(@email,@pass,"user")
    @p = Applicant.find_by(email: @email).as_json
    @project = Project.where(appl_email:@p["email"]).as_json
    @experience = Experience.where(appl_email:@p["email"]).as_json
    @education = Education.where(appl_email:@p["email"]).as_json
    hash = @p
    hash["project"] = @project
    hash["experience"] = @experience
    hash["education"] = @education
    hash.to_json
  else
    halt 404
  end
end

get "/api/comp_login/:var" do
  result = parse(params[:var])
  @email = result["comp_email"]
  @pass = result["password"]
  if auth(@email,@pass,"com")
    @c = Company.find_by(email: @email).as_json
    list = Receive.where(comp_email: @email).as_json
    @appl_list = []
    list.each do |receive|
      appl = Applicant.find_by(email: receive["appl_email"]).as_json
      @appl_list.push(appl)
    end
    hash = @c
    hash["list"] = @appl_list
    hash.to_json
  else
    halt 404
  end
end

get '/api/submit_regist_app/:var' do
  if params[:var].nil?
    halt 404
  end
  result = parse(params[:var])
  appl = Applicant.new(result)
  appl.to_json
end

get '/api/submit_regist_comp/:var' do
  if params[:var].nil?
    halt 404
  end
  result = parse(params[:var])
  comp = Company.new(result)
  comp.to_json
end

get '/api/request/:var' do
  if params[:var].nil?
    halt 404
  end
  result = parse(params[:var])

  old = Receive.find_by(appl_email: result['appl_email'], comp_email:result["comp_email"])
  if old.nil?
    request = Receive.new(result).as_json
  else
    old.destroy
  end
  appl = Applicant.find_by(email: result['appl_email'])
  r = {}
  if appl.nil?
    r['status'] = "failed"
  else
    r['status'] = "success"
  end
  r.to_json
end

get '/api/add_project/:var' do
  if params[:var].nil?
    halt 404
  end
  result = parse(params[:var])
  pro = Project.new(result)
  pro.to_json
end

get '/api/add_experience/:var' do
  if params[:var].nil?
    halt 404
  end
  result = parse(params[:var])
  exp = Experience.new(result)
  exp.to_json
end

get '/api/add_education/:var' do
  if params[:var].nil?
    halt 404
  end
  result = parse(params[:var])
  edu = Education.new(result)
  binding.pry
  edu.to_json
end

post '/api/edit_project' do
  project = params[:parameters]
  old =  Project.find_by(id:project["id"])
  r = {}
  if old.nil?
    r['status'] = "failed"
  else
    r['status'] = "success"
    old.update(title:project["title"],start_date:project["start_date"],end_date:project["end_date"],description:project["description"])
  end
  r.to_json
end

post '/api/edit_experience' do
  experience = params[:parameters]
  old = Experience.find_by(id:experience["id"])
  r = {}
  if old.nil?
    r['status'] = "failed"
  else
    r['status'] = "success"
    old.update(title:experience["title"],start_date:experience["start_date"],end_date:experience["end_date"],description:experience["description"])
  end
  r.to_json
end

post '/api/edit_education' do
  education = params[:parameters]
  old = Education.find_by(id:education["id"])
  r = {}
  if old.nil?
    r['status'] = "failed"
  else
    r['status'] = "success"
    old.update(school:education["school"],degree:education["degree"],major:education["major"],gpa:education["gpa"],description:
    education["description"],start_date:education["start_date"],end_date:education["end_date"])  
  end
  r.to_json
end

get '/api/delete_project/:id' do
  project = params[:id]
  p = Project.find_by(id:project)
  r = {}
  if p.nil?
    r['status'] = "failed"
  else
    p.destroy
    r['status'] = "success"
  end
  r.to_json
end

get '/api/delete_experience/:id' do
  experience = params[:id]
  p = Experience.find_by(id:experience)
  r = {}
  if p.nil?
    r['status'] = "failed"
  else
    p.destroy
    r['status'] = "success"
  end
  r.to_json
end

get '/api/delete_education/:id' do
  education = params[:id]
  p = Education.find_by(id:education)
  r = {}
  if p.nil?
    r['status'] = "failed"
  else
    p.destroy
    r['status'] = "success"
  end
  r.to_json
end

def parse(var)
  alist = var.split("&")
  result = {}
  for word in alist
    word_m = word.strip
    word_list = word_m.split('=')
    result[word_list[0]] = word_list[1]
  end
  return result;
end
