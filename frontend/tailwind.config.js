import daisyui from 'daisyui';

export default {
  plugins: [daisyui],
  theme: {
    extend: {},
  },
  purge: ["./index.html",'./src/**/*.{svelte,js,ts}'], //for unused css
  variants: {
    extend: {},
  },
  darkmode: false, // or 'media' or 'class'
  daisyui: {
    themes: ["light", "dark", "cupcake", 'wireframe'],
  },
}