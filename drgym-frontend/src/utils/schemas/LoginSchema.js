import * as yup from 'yup';
import YupPassword from 'yup-password';

YupPassword(yup);

const LoginSchema = () => {
  return yup.object().shape({
    email: yup
      .string()
      .email("it's not an email")
      .max(50, 'maximum 50 characters')
      .min(5, 'minimum 5 characters')
      .required("it's required"),
    password: yup
      .string()
      .max(30, 'too long')
      .min(10, 'too short')
      .minLowercase(2, 'minimum 2 lowercases')
      .minUppercase(1, 'minimum 1 uppercase')
      .minNumbers(1, 'minimum 1 number')
      .minSymbols(1, 'minimum 1 symbol')
      .required("it's required"),
  });
};

const LoginDefaultValues = () => {
  return {
    email: '',
    password: '',
  };
};

export { LoginSchema, LoginDefaultValues };
