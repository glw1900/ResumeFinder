class Recieve < ActiveRecord::Base
  belongs_to :applicant
  belongs_to :company
end
