
def check_pwd (hsh)
  if hsh[:password] == hsh[:re_password]
    new_hsh = hsh.clone
    new_hsh.delete("re_password")
    new_hsh[:gpa].to_f
    return new_hsh
  else
    return nil
  end
end

<<<<<<< HEAD:process.rb
#
=======

>>>>>>> 0489dfb353801acf6fcda97a5d8d2afccd79e156:Server/process.rb
# hsh = {"password"=>"abc","re_password"=>"abc","gpa"=>4.0}
# h = check_pwd(hsh)
# puts (hsh)
# puts (h)
