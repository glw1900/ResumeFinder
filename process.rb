require 'pry-byebug'
def check_pwd (hsh)
  if hsh[:password] == hsh[:re_password]
    new_hsh = hsh.clone
    new_hsh.delete(:re_password)
    new_hsh[:gpa].to_f
    return new_hsh
  else
    return nil
  end
end


hsh = {"password"=>"abc","re_password"=>"abc","gpa"=>4.0}
h = check_pwd(hsh)
puts (hsh)
puts (h)
