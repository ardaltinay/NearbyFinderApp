<template>
  <form @submit.prevent="handleSubmit">
    <input type="text" placeholder="Longitude" v-model="longitude">
    <input type="text" placeholder="Latitude" v-model="latitude">
    <input type="text" placeholder="Radius" v-model="radius">
    <button type="submit">Search</button>
  </form>
</template>

<script>
import axios from 'axios';

export default {
  name: 'InputForm',
  data() {
    return {
      longitude: '',
      latitude: '',
      radius: ''
    }
  },
  methods: {
    handleSubmit() {
      axios.get('/api/places/nearby', {
        params: {
          longitude: this.longitude,
          latitude: this.latitude,
          radius: this.radius
        }
      })
      .then(res => res.json())
      .then(result => console.log(result))
      .catch(err => console.log(err));
    }
  }
}
</script>

<style>

</style>