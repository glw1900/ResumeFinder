def check_pwd (hsh)
  if hsh[:password] == hsh[:re_password]
    hsh.delete(:re_password)
    hsh[:gpa].to_f
    return hsh
  else
    return nil
  end
end
