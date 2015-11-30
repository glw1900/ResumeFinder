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
    hash = @c
    hash["list"] = @appl_list
    hash.to_json
  else
    halt 404
  end
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