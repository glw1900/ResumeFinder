def check_pwd (hsh)
  if hsh[:password] == hsh[:re_password]
    hsh.delete(:re_password)
    return hsh
  else
    return nil
  end
end
